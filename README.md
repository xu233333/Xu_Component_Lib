算是SSC双端版本开发的第1步 如果失败了SSC双端版本的开发就基本宣布失败了
我之前只写过单端Mod(Port加载器只手动Port) 所以需要测试一下技术可行性 最坏的可能性就是没法做双端 那么只能Fabric和Forge二选一了(我个人倾向使用Forge 只需手搓一个类Apoli 就能享受到Forge的高级API 后续开发会十分舒服)

目前仅准备实现挂载到LivingEntity Player两个类 一个给能力引擎 另一个给SSC

~~CCA 需要挂Custom 我之后试试能不能用Mixin在它读取前注入对应的Custom~~ 没法整 还是挂Custom吧
Forge端没写过Component 之后研究一下(之前都是Mixin进对应函数附加NBT 没用过Forge的API)

注册方法: 目前仅支持LivingEntity Player两个类
双端 写一个ComponentInitializer 在registerComponent函数里注册SerializableComponent
Fabric 使用EntryPoint挂载ComponentInitializer 然后给CCA的Custom里添加对应的ID
Forge 在FMLCommonSetupEvent之前调用ComponentInitializer里的registerComponent函数 或者直接注册 不写ComponentInitializer

示例:
```java
// 共用
public class PlayerData implements SerializableComponent<Player> {
    public static ResourceLocation ID = ComponentAPI.registerPlayerComponent(new ResourceLocation("xu_mod", "player_data"), PlayerData::new);

    public Player Owner = null;
    public int tick = 0;

    public PlayerData(Player player) {
        // 不用在这里init 由引擎专门调用init
    }

    @Override
    public void save(CompoundTag compoundTag, boolean formSync) {
        // 当使用Sync时formSync为True 可以实现轻量同步
        compoundTag.putInt("tick", tick);
    }

    @Override
    public void load(CompoundTag compoundTag, boolean formSync) {
        // 当使用Sync时formSync为True 可以实现轻量同步
        tick = compoundTag.getInt("tick");
    }

    @Override
    public void init(Player componentOwner) {
        // 初始化时由引擎自动调用 不用在代码里手动调用
        this.Owner = componentOwner;
    }

    public static void initComponent() {
        // 注册组件 由Static自动调用 不过Static得加载类时执行 所以需要外部触发加载
    }
}
```
```java
// Forge 端
@Mod(Test_Forge.MODID)
public class Test_Forge {
    public static final String MODID = "test_forge";

    public Test_Forge() {
        // 必须在 FMLCommonSetupEvent 之前注册
        PlayerData.initComponent();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        for (Player player : event.getServer().getPlayerList().getPlayers()) {
            if (ComponentAPI.getPlayerComponent(player, PlayerData.ID) instanceof PlayerData playerData) {
                playerData.tick ++;
                ComponentAPI.syncPlayerComponent(player, PlayerData.ID);  // 更新到客户端 如果客户端不需要这些数据 可以不同步
            }
        }
    }
}
```
```java
// Fabric 端
// TODO 没写 之后再补 类似CCA 需要挂EntryPoint(xu_component_lib)和Custom(CCA的Custom) 肯定没Forge方便
```

Gradle 配置(没有maven仓库 需要本地导入 等开发的差不多再发布) 把Mod jar放进/libs文件夹里:
```
repositories {
    flatDir {
        dir 'libs'
    }
}

dependencies {
    implementation fg.deobf('xu_mod.xu_component_lib:Xu_Component_Lib-1.0.0-alpha:1.0.0-alpha')
}
```