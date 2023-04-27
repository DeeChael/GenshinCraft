package net.deechael.dynamic.quest.api.quest;

import net.deechael.dynamic.quest.api.requirement.Requirement;
import net.deechael.dynamic.quest.api.reward.Reward;
import org.bukkit.plugin.Plugin;

import java.util.List;

public interface Quest extends Requirement {

    /**
     * Get plugin which provides this quest
     *
     * @return provider plugin
     */
    Plugin getProvider();

    /**
     * Get the identifier of the quest
     *
     * @return identifier
     */
    String getIdentifier();

    /**
     * Get the display name of the quest
     *
     * @return display name
     */
    String getDisplayName();

    /**
     * Get the description of the quest
     *
     * @return description
     */
    String getDescription();

    /**
     * Get if the quest is re-completable
     *
     * @return status
     */
    boolean isRecompletable();

    /**
     * Get all the rewards can get after completing the quest
     *
     * @return rewards
     */
    List<? extends Reward> getRewards();

    /**
     * Get the requirements to complete the quest
     *
     * @return requirements
     */
    List<? extends Requirement> getRequirements();

}
