package net.deechael.dynamic.quest.api.requirement;

import org.bukkit.Material;

import java.util.Map;

public interface SubmitItemRequirement extends Requirement<Material> {

    Map<Material, Integer> items();

}
