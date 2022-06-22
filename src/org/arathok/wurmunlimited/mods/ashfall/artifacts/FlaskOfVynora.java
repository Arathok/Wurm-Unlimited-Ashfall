package org.arathok.wurmunlimited.mods.ashfall.artifacts;

import com.wurmonline.server.behaviours.Vehicle;
import com.wurmonline.server.behaviours.Vehicles;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.items.Materials;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.IconConstants;
import com.wurmonline.shared.util.MaterialUtilities;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.gotti.wurmunlimited.modsupport.items.ModItems;
import org.gotti.wurmunlimited.modsupport.vehicles.ModVehicleBehaviour;
import org.gotti.wurmunlimited.modsupport.vehicles.ModVehicleBehaviours;
import org.gotti.wurmunlimited.modsupport.vehicles.VehicleFacade;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FlaskOfVynora {
 public static int flaskOfVynoraId;
 public static ItemTemplate flaskOfVynora;
    private static final Set<Long> embarkingIds = new HashSet<>();

    private static void registerFlaskOfVynora() throws IOException {
        flaskOfVynora = new ItemTemplateBuilder("arathok.ashfall.vynoraArtifact").name("Flask of Vynora", "Flask of Vynora",
                        "Fo stole it to create the volcano eruption creating Ashfall. It contains the essence of the Ocean which makes its user swim faster.")
                .modelName("model.item.valrei.")
                .imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_TOOL,
                        ItemTypes.ITEM_TYPE_METAL,
                        ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET,
                        ItemTypes.ITEM_TYPE_NOTRADE,
                        ItemTypes.ITEM_TYPE_NOSELLBACK,
                        ItemTypes.ITEM_TYPE_ARTIFACT,
                        ItemTypes.ITEM_TYPE_HOLLOW,
                        ItemTypes.ITEM_TYPE_VEHICLE,
                        ItemTypes.ITEM_TYPE_NOPUT,
                        ItemTypes.ITEM_TYPE_DECORATION,

                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(10, 10, 10)
                .weightGrams(10).material(Materials.MATERIAL_ADAMANTINE)
                .behaviourType((short) 1) // Item
                .primarySkill(SkillList.GROUP_RELIGION)
                .difficulty(90) // no hard lock
                .build();

        flaskOfVynoraId = flaskOfVynora.getTemplateId();
        ModItems.addModelNameProvider(flaskOfVynoraId, item -> {
            StringBuilder name = new StringBuilder(flaskOfVynora.getModelName());

            if (!embarkingIds.contains(item.getWurmId())) {
                Vehicle v = Vehicles.getVehicleForId(item.getWurmId());
                if (v == null || v.getPilotSeat() == null || v.getPilotSeat().getOccupant() == -10L)
                    name.append("standing.");
            }

            name.append(MaterialUtilities.getMaterialString(item.getMaterial()));

            return name.toString();
        });

        ModVehicleBehaviours.addItemVehicle(flaskOfVynoraId, new ModVehicleBehaviour() {
            @Override
            public void setSettingsForVehicle(Creature creature, Vehicle vehicle) {
            }

            @Override
            public void setSettingsForVehicle(Item item, Vehicle vehicle) {
                VehicleFacade facade = wrap(vehicle);

                facade.createPassengerSeats(0);
                facade.setSeatOffset(0, -0.43f, 0, 0f);
                facade.setSeatFightMod(0, 0.7f, 0.9f);
                vehicle.maxHeight=-2.0F;
                facade.setMaxSpeed(50F);
                facade.setMaxDepth(-2499.99f); // must be > -2500 due to wogic
                //facade.setMaxHeightDiff(1F);
                vehicle.setSkillNeeded(0);
                vehicle.commandType=1;
            }
        });
    }

    public static void setEmbarking(Item item, boolean embarking) {
        if (embarking)
            embarkingIds.add(item.getWurmId());
        else
            embarkingIds.remove(item.getWurmId());
    }
    public static void register() throws IOException {


        registerFlaskOfVynora();

    }
}

