package com.overload.io.packets.outgoing.impl;

import com.overload.io.packets.outgoing.OutgoingPacket;
import com.overload.io.packets.outgoing.PacketBuilder;

public class RegionChange implements OutgoingPacket {

	@Override
    public PacketBuilder create() {
    	PacketBuilder buf = new PacketBuilder(210);
        buf.putInt(0x3f008edd);
        return buf;
    }

}