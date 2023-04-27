package net.deechael.dynamic.quest.api.requirement;

import org.bukkit.entity.EntityType;

import java.util.Map;

public interface KIllEntityRequirement extends Requirement<EntityType> {

    Map<EntityType, Integer> entities();

}
