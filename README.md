# RationLayout（比例布局）

    1。根据宽度设置高度比例
    2。根据高度设置宽度比例

## 参数

|参数|类型|取值范围|默认值|
|---|---|---|---|
|km_ratio|float|0.0f～100.0f|0.0f
|orientation|int|horizontal/vertical|horizontal

## 参数详情

### orientation 约束方向

    android:orientation="horizontal"

    android:width可以是warp_content/match_parent/**dp

    此时 height 是 宽度的一定比例（km_ratio）

### km_ratio 约束比例

    app:km_ratio="1"

    约束比例是1.0,此时显示会是正方形

    默认值是0.0，不进行约束。




## 例子

```xml
<com.komlin.share.ratiolayout.RatioLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:km_ratio="1">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="10dp"
            android:text="Hello World!" />

    </com.komlin.share.ratiolayout.RatioLayout>
```

上例中，根据宽度填充，高度是宽度的1.0倍，也就是正方形。


## How to

#### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### Step 2. Add the dependency
```java
dependencies {
    implementation 'com.github.zyawei:RatioLayout:1.1'
}
```