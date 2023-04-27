package ltd.kumo.plutomc.framework.shared.command;

import ltd.kumo.plutomc.framework.shared.command.executors.Executor;
import ltd.kumo.plutomc.framework.shared.command.executors.PlayerExecutor;
import org.bukkit.command.CommandSender;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Command {

    /**
     * 获取本指令的名称
     *
     * @return 名称
     */
    String name();

    Command suggests(Consumer<Suggestion> provider);

    /**
     * 判断一个发送者是否有权限执行，如果返回值是false，则不会提供对应的指令提示
     *
     * @param requirement 需求
     * @return 自己
     */
    Command requires(Predicate<CommandSender> requirement);

    /**
     * 为指令添加别名
     *
     * @param aliases 别名
     * @return 自己
     */
    Command aliases(String... aliases);

    /**
     * 设置执行器
     *
     * @param executor 执行器
     * @return 自己
     */
    Command executes(Executor executor);

    /**
     * 设置玩家执行器，不是玩家执行不会调用执行器
     *
     * @param executor 执行器
     * @return 自己
     */
    Command executesPlayer(PlayerExecutor executor);

    /**
     * 添加指令子节点
     *
     * @param name 指令名称
     * @return 子节点指令
     */
    Command then(String name);

    /**
     * 添加参数子节点
     *
     * @param name 参数名称
     * @param type 参数类型的类
     * @param <E>  参数类型
     * @return 子节点指令
     */
    <E extends Argument<?>> Command then(String name, Class<E> type);

    /**
     * 添加整型参数子节点
     *
     * @param name 参数名称
     * @param min  最小值
     * @param max  最大值
     * @return 子节点指令
     */
    Command thenInteger(String name, int min, int max);

    /**
     * 添加长整型参数子节点
     *
     * @param name 参数名称
     * @param min  最小值
     * @param max  最大值
     * @return 子节点指令
     */
    Command thenLong(String name, long min, long max);

    /**
     * 添加单精度浮点型参数子节点
     *
     * @param name 参数名称
     * @param min  最小值
     * @param max  最大值
     * @return 子节点指令
     */
    Command thenFloat(String name, float min, float max);

    /**
     * 添加双精度浮点型参数子节点
     *
     * @param name 参数名称
     * @param min  最小值
     * @param max  最大值
     * @return 子节点指令
     */
    Command thenDouble(String name, double min, double max);

    /**
     * 复制一个除了名字以外完全相同的指令
     *
     * @param name 新名称
     * @return 新指令
     */
    Command clone(String name);

}
