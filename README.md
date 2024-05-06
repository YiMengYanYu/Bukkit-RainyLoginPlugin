# Bukkit-RainyLoginPlugin

### 介绍

java版我的世界基于BukkitAPI的登录插件


### 使用说明

1.放入插件目录即可运行

2.理论支持1.12.2以上我的世界版本

3.支持spigot paper purpur 服务端

4.请不要使用服务的reload命令我们不会处理您reload产生的任何问题

### 插件亮点

**安全性**：我们的插件永远开源，代码任何人可以审查，保证您的服务器安全

**独立性**：开发者希望创建一款不依赖于其他插件的独立插件。这有助于简化安装和配置过程，减少插件之间的依赖关系，降低潜在的冲突和报错风险。

**易用性**：插件的设计考虑了用户的便捷性。大量的默认配置和中文注释使得用户即使在没有深入了解插件细节的情况下，也能轻松上手，减少配置和使用的难度。

**针对性**：插件专注于中文支持，这可能是因为开发者主要面向中文用户群体，开发者认为中文用户可能更需要这样的工具。对于英文水平不高的用户或者服主来说，这款插件提供了一个友好且易于理解的使用界面，使得用户无需担心语言障碍。

**更好的支持**：这款插件是免费的，且你可以询问作者任何插件的问题，询问问题请在Gitee lssues中提交问题[Issues · 忆梦烟雨/Bukkit-RainyLoginPlugin - Gitee.com](https://gitee.com/YiMengYanYu/Bukkit-RainyLoginPlugin/issues)

### 配置文件说明

#### **RainyLogin主配置文件**

RainyLogin/login-config.yml

```yaml
whitelist:
  #白名单总开关 添加白名单命令 /bai add
  whitelistCheckSwitch: false

ipLogin:
  #IP登录总开关
  ipLoginCheckSwitch: false

#此功能连接QQ机器人尚未开发
webSocket:
  #WebSocket总开关
  webSocketCheckSwitch: false
  webSocketUrl: ws://localhost:8080/
```

#### **存储密码校验策略配置文件**

RainyLogin/password-policy-config.yml 

```yaml
#请不要删除任何配置，否则会影响系统运行
password:
  #密码检测总控开关
  passwordCheckSwitch: true
  #密码长度控制开关
  checkPasswordLength: true
  #密码最小长度
  limitPasswordMinLength: 6
  #密码最大长度
  limitPasswordMaxLength: 20
  #密码是否必须包含数字
  checkContainDigit: true
  #密码是否必须包含字母
  checkContainUpperLowerCase: true
  #密码是否必须包含小写字母
  checkContainLowerCase: false
  #密码是否必须包含大写字母
  checkContainUpperCase: false
  #密码是否必须包含特殊字符
  checkContainSpecialChar: false
  #密码中不允许的字符集合
  defaultSpecialChar: "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"
  #键盘横向连续检测开关，如qwer等
  checkHorizontalKeySequential: false
  #键盘横向连续检测最小长度 配置3 qw可以通过检测 qwr qwer qwerty等都无法通过检测
  limitHorizontalKeyNumber: 3
  #键盘斜向连续检测开关 如qaz、wsx edc rfv等
  checkSlopeKeySequential: false
  #键盘斜向连续检测最小长度 最大只能只能是3
  limitSlopeKeyNumber: 3
  #逻辑位置连续检测开关，如1234、abcd等
  checkLogicSequential: false
  #逻辑位置连续检测最小长度
  limitLogicNumber: 4
  #相邻字符相同检测开关，如aaa、111、###等
  checkSequentialCharSame: false
  #相邻字符相同检测最小长度
  limitSequentialCharNumber: 3
  #自定义密码检测开关，如固定字符等 此功能是配置黑名单字符串的开关 此版本暂时不支持
  checkCustomPasswordArray: true
```



### 玩家数据文件

####  **存储玩家账号密码信息文件** 

RainyLogin/player_passwords.yml

```yaml
#这个文件请不要配置，这个是注册玩家存储密码的文件
玩家名称:
  password: '玩家注册时的密码'
  uuid: 玩家的uuid
```

#### **白名单列表文件**

RainyLogin/whitelist.yml 

```yaml
whitelist:
- 玩家名称


```

### 命令与命令权限

|                命令                |   命令介绍   |      权限名称       | 默认拥有权限的玩家 |
| :--------------------------------: | :----------: | :-----------------: | :----------------: |
|         /login <登录密码>          |     登录     |  rainyLogin.login   |      所有玩家      |
| /register <密码>  <第二次输入密码> |     注册     | rainyLogin.register |      所有玩家      |
|                /bai                |  白名单相关  |   rainyLogin.bai    |         op         |
|                /rws                | 重连QQ机器人 |         无          |         op         |



