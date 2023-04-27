package net.deechael.dynamic.quest;

import net.deechael.dynamic.quest.api.quest.Quest;
import net.deechael.dynamic.quest.api.quest.QuestBuilder;
import net.deechael.dynamic.quest.api.quest.QuestPlayer;
import net.deechael.dynamic.quest.api.requirement.Requirement;
import net.deechael.dynamic.quest.api.requirement.RequirementBuilder;
import net.deechael.dynamic.quest.api.reward.Reward;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DynamicQuest {

    private final static Map<Class<?>, Class<?>> implementations = new HashMap<>();

    private final static Map<Class<? extends Requirement<?>>, Class<? extends RequirementBuilder<?, ?>>> requirements = new HashMap<>();

    private final static RuntimeException NOT_IMPLEMENTED = new RuntimeException("Not Implemented");

    private static RuntimeException notImplemented() {
        return NOT_IMPLEMENTED;
    }

    @NotNull
    public static QuestBuilder createQuest(@NotNull Plugin provider, @NotNull String identifier) {
        throw notImplemented();
    }

    public static void registerQuest(@NotNull Quest quest) {
        throw notImplemented();
    }

    public static void unregisterQuest(@NotNull Quest quest) {
        throw notImplemented();
    }

    @NotNull
    public static Set<? extends Quest> getQuests(Plugin provider) {
        throw notImplemented();
    }

    @Nullable
    public static Quest getQuest(@NotNull Plugin provider, @NotNull String identifier) {
        throw notImplemented();
    }

    @Nullable
    public static Quest getQuest(@NotNull String identifier) {
        throw notImplemented();
    }

    public static QuestPlayer getQuestPlayer(OfflinePlayer player) {
        throw notImplemented();
    }

    public static QuestPlayer getQuestPlayer(UUID uuid) {
        throw notImplemented();
    }

    public static <T extends Reward> T createReward(Class<T> rewardClass) {
        if (!implementations.containsKey(rewardClass))
            throw new RuntimeException("Reward implementation not found");
        try {
            return rewardClass.cast(implementations.get(rewardClass).getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, E extends Requirement<T>> RequirementBuilder<T, E> createRequirement(Class<E> requirementType) {
        try {
            return (RequirementBuilder<T, E>) requirements.get(requirementType).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, E extends Requirement<T>, S extends RequirementBuilder<T, E>> void registerRequirementType(Class<E> requirement, Class<S> requirementBuilder) {
        requirements.put(requirement, requirementBuilder);
    }

    public static <T, S extends T> void registerImplementation(Class<T> interf, Class<S> implementation) {
        if (implementations.containsKey(interf))
            throw new RuntimeException("Interface has had implementation already");
        if (implementations.containsValue(implementation))
            throw new RuntimeException("Implementation has had referring interface already");
        implementations.put(interf, implementation);
    }

}
