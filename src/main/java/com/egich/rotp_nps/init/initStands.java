package com.egich.rotp_nps.init;

import com.egich.rotp_nps.AddonMain;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.stats.ArmoredStandStats;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.stats.TimeStopperStandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import com.github.standobyte.jojo.entity.stand.stands.CrazyDiamondEntity;
import com.github.standobyte.jojo.entity.stand.stands.HierophantGreenEntity;
import com.github.standobyte.jojo.entity.stand.stands.MagiciansRedEntity;
import com.github.standobyte.jojo.entity.stand.stands.SilverChariotEntity;
import com.github.standobyte.jojo.entity.stand.stands.StarPlatinumEntity;
import com.github.standobyte.jojo.entity.stand.stands.TheWorldEntity;

import static com.github.standobyte.jojo.init.power.stand.ModStandsInit.STAND_CRAZY_DIAMOND;
import static com.github.standobyte.jojo.init.power.stand.ModStandsInit.STAND_HIEROPHANT_GREEN;
import static com.github.standobyte.jojo.init.power.stand.ModStandsInit.STAND_MAGICIANS_RED;
import static com.github.standobyte.jojo.init.power.stand.ModStandsInit.STAND_SILVER_CHARIOT;
import static com.github.standobyte.jojo.init.power.stand.ModStandsInit.STAND_STAR_PLATINUM;
import static com.github.standobyte.jojo.init.power.stand.ModStandsInit.STAND_THE_WORLD;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public class initStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), AddonMain.MOD_ID);

    public static final EntityStandRegistryObject.EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<CrazyDiamondEntity>>
            CD = new EntityStandRegistryObject.EntityStandSupplier<>(STAND_CRAZY_DIAMOND);

    public static final EntityStandRegistryObject.EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<HierophantGreenEntity>>
            HG = new EntityStandRegistryObject.EntityStandSupplier<>(STAND_HIEROPHANT_GREEN);

    public static final EntityStandRegistryObject.EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<MagiciansRedEntity>>
            MR = new EntityStandRegistryObject.EntityStandSupplier<>(STAND_MAGICIANS_RED);

    public static final EntityStandRegistryObject.EntityStandSupplier<EntityStandType<ArmoredStandStats>, StandEntityType<SilverChariotEntity>>
            SC = new EntityStandRegistryObject.EntityStandSupplier<>(STAND_SILVER_CHARIOT);

    public static final EntityStandRegistryObject.EntityStandSupplier<EntityStandType<TimeStopperStandStats>, StandEntityType<StarPlatinumEntity>>
            SP = new EntityStandRegistryObject.EntityStandSupplier<>(STAND_STAR_PLATINUM);

    public static final EntityStandRegistryObject.EntityStandSupplier<EntityStandType<TimeStopperStandStats>, StandEntityType<TheWorldEntity>>
            TW = new EntityStandRegistryObject.EntityStandSupplier<>(STAND_THE_WORLD);



    @Mod.EventBusSubscriber(modid = AddonMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class AddonRegister {
        private static final Field STAND_STATS_ARROW_RANDOM_WEIGHT = getField(StandStats.class, "randomWeight");

        @SubscribeEvent(priority = EventPriority.HIGH)
        public static void afterStandsRegister(@Nonnull RegistryEvent.Register<StandType<?>> event) {
            StandType<?> CrazyDiamond = CD.getStandType();
            StandType<?> HierophantGreen = HG.getStandType();
            StandType<?> MagiciansRed = MR.getStandType();
            StandType<?> SilverChariot = SC.getStandType();
            StandType<?> StarPlatinum = SP.getStandType();
            StandType<?> TheWorld = TW.getStandType();
            try {
                initStand(CrazyDiamond);
                initStand(HierophantGreen);
                initStand(MagiciansRed);
                initStand(SilverChariot);
                initStand(StarPlatinum);
                initStand(TheWorld);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }


        private static void initStand(StandType<?> Stand) throws IllegalAccessException {
            StandStats defaultStats = Stand.getDefaultStats();
            STAND_STATS_ARROW_RANDOM_WEIGHT.setDouble(defaultStats, 0);
        }

        private static Field getField(Class<?> clazz, String fieldName) {
            try {
                Field f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
