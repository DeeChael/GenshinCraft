package net.deechael.dynamic.quest.api.reward;

public interface ExperienceRewardBuilder extends Reward {

    /**
     * Set to give how many xps
     *
     * @param xps xp amount
     * @return self
     */
    ExperienceRewardBuilder xp(int xps);

    /**
     * Set to give how many levels
     *
     * @param levels level amount
     * @return self
     */
    ExperienceRewardBuilder level(int levels);

}
