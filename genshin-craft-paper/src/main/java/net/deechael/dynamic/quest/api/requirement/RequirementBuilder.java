package net.deechael.dynamic.quest.api.requirement;

public interface RequirementBuilder<T, E extends Requirement<T>> {

    RequirementBuilder<T, E> data(T t, int amount);

    E build();

}
