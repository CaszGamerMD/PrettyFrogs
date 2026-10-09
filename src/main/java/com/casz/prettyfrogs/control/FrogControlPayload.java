package com.casz.prettyfrogs.control;

import com.casz.prettyfrogs.PrettyFrogs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/** Only the action enum is transmitted; the server chooses the frog and target. */
public record FrogControlPayload(byte action) implements CustomPacketPayload {
    public static final byte CROAK = 0;
    public static final byte TONGUE = 1;
    public static final Type<FrogControlPayload> TYPE =
            new Type<>(PrettyFrogs.id("frog_control"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FrogControlPayload> CODEC =
            CustomPacketPayload.codec(FrogControlPayload::write, FrogControlPayload::new);

    private FrogControlPayload(RegistryFriendlyByteBuf buffer) {
        this(buffer.readByte());
    }

    private void write(RegistryFriendlyByteBuf buffer) {
        buffer.writeByte(action);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
