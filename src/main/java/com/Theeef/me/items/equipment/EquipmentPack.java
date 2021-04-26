package com.Theeef.me.items.equipment;

import com.Theeef.me.items.containers.ContainerType;
import com.Theeef.me.items.containers.DNDContainerItem;

public class EquipmentPack {

    public static DNDContainerItem BURGLARS_PACK = new DNDContainerItem(ContainerType.BACKPACK, "Burglar's Pack", 16, AdventuringGear.BALL_BEARINGS, AdventuringGear.BELL, AdventuringGear.CANDLE.getAmount(5), AdventuringGear.CROWBAR, AdventuringGear.HAMMER, AdventuringGear.PITON.getAmount(10), AdventuringGear.HOODED_LANTERN, AdventuringGear.OIL_FLASK.getAmount(2), AdventuringGear.RATIONS.getAmount(5), AdventuringGear.TINDERBOX, AdventuringGear.WATERSKIN, AdventuringGear.ROPE);
    public static DNDContainerItem DIPLOTMATS_PACK = new DNDContainerItem(ContainerType.CHEST, "Diplomat's Pack", 39, AdventuringGear.CASE_MAP_SCROLL.getAmount(2), AdventuringGear.FINE_HAT, AdventuringGear.FINE_SHIRT, AdventuringGear.FINE_PANTS, AdventuringGear.FINE_SHOES, AdventuringGear.INK_BOTTLE, AdventuringGear.INK_PEN, AdventuringGear.LAMP, AdventuringGear.OIL_FLASK.getAmount(2), AdventuringGear.PAPER.getAmount(5), AdventuringGear.PERFUME_VIAL, AdventuringGear.SEALING_WAX, AdventuringGear.SOAP);
    public static DNDContainerItem DUNGEONEERS_PACK = new DNDContainerItem(ContainerType.BACKPACK, "Dungeoneer's Pack", AdventuringGear.CROWBAR, AdventuringGear.HAMMER, AdventuringGear.PITON.getAmount(10), AdventuringGear.TORCH.getAmount(10), AdventuringGear.TINDERBOX, AdventuringGear.RATIONS.getAmount(10), AdventuringGear.WATERSKIN, AdventuringGear.ROPE);
    public static DNDContainerItem EXPLORERS_PACK = new DNDContainerItem(ContainerType.BACKPACK, "Explorer's Pack", AdventuringGear.BEDROLL, AdventuringGear.MESS_KIT, AdventuringGear.TINDERBOX, AdventuringGear.TORCH.getAmount(10), AdventuringGear.RATIONS.getAmount(10), AdventuringGear.WATERSKIN, AdventuringGear.ROPE);
    public static DNDContainerItem PRIESTS_PACK = new DNDContainerItem(ContainerType.BACKPACK, "Priest's Pack", AdventuringGear.BLANKET, AdventuringGear.CANDLE.getAmount(10), AdventuringGear.TINDERBOX, AdventuringGear.ALMS_BOX, AdventuringGear.INCENSE_BLOCK.getAmount(2), AdventuringGear.CENSER, AdventuringGear.VESTMENTS, AdventuringGear.RATIONS.getAmount(2), AdventuringGear.WATERSKIN);
    public static DNDContainerItem SCHOLARS_PACK = new DNDContainerItem(ContainerType.BACKPACK, "Scholar's Pack", AdventuringGear.BOOK, AdventuringGear.INK_BOTTLE, AdventuringGear.INK_PEN, AdventuringGear.PARCHMENT.getAmount(1), AdventuringGear.LITTLE_BIG_OF_SAND, AdventuringGear.SMALL_KNIFE);

}
