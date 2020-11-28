![Android CI](https://github.com/zhuzichu520/Developer/workflows/Android%20CI/badge.svg)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/692b03eb8e3346c2a57e7b471d691ca0)](https://www.codacy.com/gh/zhuzichu520/Developer/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=zhuzichu520/Developer&amp;utm_campaign=Badge_Grade)
[![](https://jitpack.io/v/zhuzichu520/Developer.svg)](https://jitpack.io/#zhuzichu520/Developer)

---

# 目录

1. [Gradle依赖](#Gradle依赖)
2. [组建化配置](#组建化配置)


## Gradle 依赖

在根目录的`build.gradle`中加入

```
allprojects {
    repositories {
		...
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

在module中的`build.gradle`文件中添加:

```
dependencies {
  implementation 'com.github.zhuzichu520.Developer:library-libs:3.1.5'
  implementation 'com.github.zhuzichu520.Developer:library-widget:3.1.5'
  implementation 'com.github.zhuzichu520.Developer:library-mvvm:3.1.5'
}
```

## 组建化配置

第一步：将项目中的buildSrc拷贝到项目中

第二步：将组件化module中的`build.gradle`的代码改成module.InitModule(project)，代码如下：

```kotlin
module.InitModule(project)
```

