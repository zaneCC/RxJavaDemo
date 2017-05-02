# RxJavaDemo
RxJava demo 是一个 针对 RxJava2.0 在 android 平台上的栗子。

demo包括：

1、RxJava三部曲
* 创建一个 Observable（上游）
* 创建一个 Observer（下游）
* 建立连接

2、让RxJava配合Activity生命周期使用

* 使用 Disposable 对 Observable（上游）与 Observer（下游）解除绑定
* CompositeDisposable 的使用

3、RxJava操作符

变换操作：
  * map
  * flatMap
  * concatMap

过滤操作：
  * filter
  * simple

结合操作：
  * zip

辅助操作：
  * timeout
  * delay

创建操作：
  * interval

