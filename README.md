# Android-AOP-AspectJ

shows how to use permission check，log trace，method spended time，network check... with AspectJ

## Features
|注解|作用|备注|
|:---|:---|:---|
|@CheckNet(isShowTips = true)|检查网络连接状态，如果连接正常，则会正常执行被注解的方法，否则不执行| isShowTips ：当网络断开的情况下，是否提示用户|
|@LogTrace(level = LogLevel.TYPE_INFO,traceSpendTime = false)|进入方法时，打印方法名和参数值，退出方法时，打印方法名和返回值| level ：日志的等级（v,d,i,w,e），<br>traceSpendTime 为true表示打印方法执行的时间|
| @CheckPermission(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE} ,requestCode = 1)|在被注解的方法之前检查相应的权限|可同时配置多个权限，<br>当所有权限授权时，则会正常执行被注解的方法，否则不执行|
|...|...|...|

## Sample Usage

@CheckNet：检查网络

```java
  @CheckNet(isShowTips = true)
    private void netCheck() {
        Log.i(TAG, "网络已经连接，执行逻辑");
    };

```

@LogTrace：打印方法，参数值，返回值，执行时间

```java
@LogTrace(level = LogLevel.TYPE_INFO,traceSpendTime = true)
public String getName(String first, String last) {
    SystemClock.sleep(15); 
    return first + " " + last;
}

```
![image](https://github.com/myjoybar/Android-AOP-AspectJ/blob/master/screenshots/screenshot.jpg)

@CheckPermission：权限检查

```java

@CheckPermission(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA} ,requestCode = 1)
private void permissionCheck() {
    Log.i(TAG, "已经检查权限，执行授予权限后的逻辑");
}
```
## License

Copyright 2018 MyJoybar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.  