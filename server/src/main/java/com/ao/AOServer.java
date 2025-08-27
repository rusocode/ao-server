package com.ao;

import com.ao.context.ApplicationContext;
import com.ao.network.ClientPacketsManager;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.ServerPacketsManager;
import com.ao.network.packet.OutgoingPacket;
import com.ao.security.SecurityManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Central server class that handles network connections.
 */

public class AOServer implements Runnable {

    private static final SecurityManager security = ApplicationContext.getInstance(SecurityManager.class);
    private static final AttributeKey<Connection> CONNECTION_KEY = AttributeKey.valueOf("connection");

    private InetSocketAddress listeningAddr;
    private int backlog;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    /**
     * Starts running the AOServer. It MUST be properly initialized beforehand.
     */
    @Override
    public void run() {
        // Configure the server
        bossGroup = new NioEventLoopGroup(); // Accept connections
        workerGroup = new NioEventLoopGroup(); // Handle I/O operations

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, backlog)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            /*
                             * Netty processes data by running it through a pipe that works both
                             * for incoming and outgoing data. Decoders are applied to incoming data,
                             * encoders to outgoing.
                             *
                             * The order of elements is important since it impacts on the order of execution.
                             * This scheme allows for better separation of concerns and to add/remove
                             * steps easily and free of side effects. For instance, we may want to add an
                             * inflate/deflate step using zlib, and that is independent of encryption
                             * and the AO protocol itself.
                             */

                            // Add our security layer to the pipeline
                            pipeline.addLast("encrypter", new MessageToMessageEncoder<ByteBuf>() {
                                @Override
                                protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
                                    ByteBuf buffer = msg.copy(); // Create a copy to avoid modifying the original
                                    security.encrypt(buffer, ctx.channel());
                                    out.add(buffer);
                                }
                            });

                            pipeline.addLast("decrypter", new MessageToMessageDecoder<ByteBuf>() {
                                @Override
                                protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
                                    security.decrypt(msg, ctx.channel());
                                    out.add(msg.retain()); // Retain the reference since we're passing it along
                                }
                            });

                            /*
                             * Decoder for streamed data.
                             *
                             * Notice handling is performed as decoding happens, since we are processing a stream.
                             */
                            pipeline.addLast("decoder", new ByteToMessageDecoder() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    super.channelActive(ctx);
                                    // New user is connected, get it ready for action!
                                    ctx.channel().attr(CONNECTION_KEY).set(new Connection(ctx.channel()));
                                }

                                @Override
                                protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                                    in.markReaderIndex();
                                    boolean processed = false;

                                    try {
                                        Connection connection = ctx.channel().attr(CONNECTION_KEY).get();
                                        processed = ClientPacketsManager.handle(new DataBuffer(in), connection);
                                    } catch (IndexOutOfBoundsException e) {
                                        // Not enough data, ignore it
                                    }

                                    if (!processed) {
                                        in.resetReaderIndex();
                                        return; // Wait for more data
                                    }

                                    out.add(in.readBytes(in.readableBytes())); // Pass any remaining data along
                                }
                            });

                            pipeline.addLast("encoder", new MessageToMessageEncoder<OutgoingPacket>() {
                                @Override
                                protected void encode(ChannelHandlerContext ctx, OutgoingPacket packet, List<Object> out) throws Exception {
                                    ByteBuf byteBuf = Unpooled.buffer();

                                    // Wrap the ByteBuf in our data buffer and write the packet into it
                                    DataBuffer buffer = new DataBuffer(byteBuf);
                                    ServerPacketsManager.write(packet, buffer);

                                    out.add(byteBuf);
                                }
                            });
                        }
                    });

            // Bind and start to accept incoming connections.
            ChannelFuture future = bootstrap.bind(listeningAddr).sync();

            // Wait until the server socket is closed
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            shutdown();
        }
    }

    /**
     * Gracefully shutdown the server
     */
    public void shutdown() {
        if (bossGroup != null) bossGroup.shutdownGracefully();
        if (workerGroup != null) workerGroup.shutdownGracefully();
    }

    /**
     * @param listeningAddr listeningAddr to set
     */
    public void setListeningAddr(InetSocketAddress listeningAddr) {
        this.listeningAddr = listeningAddr;
    }

    /**
     * @param backlog backlog to set
     */
    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

}