package net.deechael.dynamic.quest.api.quest;

import net.deechael.dynamic.quest.api.requirement.Requirement;
import net.deechael.dynamic.quest.api.reward.Reward;

public interface QuestBuilder {

    /**
     * Set display name
     *
     * @param displayName display name
     * @return builder
     */
    QuestBuilder displayName(String displayName);

    /**
     * Set description
     *
     * @param description description
     * @return builder
     */
    QuestBuilder description(String description);

    /**
     * Add rewards
     *
     * @param rewards rewards
     * @return builder
     */
    QuestBuilder reward(Reward... rewards);

    /**
     * Add requirements
     *
     * @param requirements requirements
     * @return builder
     */
    QuestBuilder require(Requirement... requirements);

    /**
     * Set this quest as re-completable
     *
     * @return builder
     */
    QuestBuilder recompletable();

    /**
     * Build the quest
     *
     * @return built quest
     */
    Quest build();

}
