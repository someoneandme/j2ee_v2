以前spring是靠quartz来配置定时任务。
但是quartz的定位很不清晰，产品感很弱。
仅仅在动态配置或db配置方面就很弱。

之前的项目quartz仅仅作为静态配置的任务。

java本身有Timer支持调度，spring现在自己也支持了。

@Scheduled

@Aysnc