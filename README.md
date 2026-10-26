# Quarkus + Qute 开发启动模板

一个简单的服务端渲染项目：Java 21、Quarkus 3.27.1（3.27 LTS 系列）、Qute、Quarkus REST 和 Jackson。无需 Node.js、前端构建工具或数据库。

## 本地启动

安装 **JDK 21**，设置好 `JAVA_HOME`。已提供 Maven Wrapper，无需单独安装 Maven；首次运行需要网络下载 Maven 和依赖。

```bash
./mvnw quarkus:dev
```

Windows 使用 `mvnw.cmd quarkus:dev`。

- 首页：<http://localhost:8080/>
- 动态渲染：<http://localhost:8080/?name=Qute>
- JSON 接口：<http://localhost:8080/api/hello?name=Qute>
- 开发工具：<http://localhost:8080/q/dev/>（仅开发模式）

修改 Java、HTML 或 CSS 后刷新页面，Quarkus 会自动重新加载。开发模式默认启用调试端口 `5005`。

## 项目结构

```text
src/main/java/com/example/web/
  HomeResource.java              # GET /，使用类型安全的 Qute 模板
  GreetingResource.java          # GET /api/hello，返回 JSON
src/main/resources/
  application.properties         # 应用配置
  templates/
    layout.html                  # 公共布局
    HomeResource/index.html      # 首页，继承公共布局
  META-INF/resources/css/app.css # 静态样式，访问路径 /css/app.css
src/test/java/com/example/web/
  StarterResourceTest.java       # 页面、转义、接口和静态资源测试
```

Qute 的 `@CheckedTemplate` 会在构建时检查模板与参数。`Templates.index(String name)` 对应 `templates/HomeResource/index.html`。新增页面时可按相同方式添加模板方法和 HTML 文件。

HTML 模板中的动态值默认自动转义，不要对用户输入使用 `.raw`。模板中的 `{#include layout}` 和 `{#insert content}` 用于布局复用。

## 测试与打包

```bash
./mvnw test
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

部署时需要复制整个 `target/quarkus-app/` 目录，而不只是 `quarkus-run.jar`。打包后的程序同样需要 Java 21。

覆盖端口：

```bash
QUARKUS_HTTP_PORT=9090 ./mvnw quarkus:dev
```

容器或远程环境如需对外监听，显式设置 `QUARKUS_HTTP_HOST=0.0.0.0`。常规配置放在 `application.properties`，也可以通过环境变量覆盖。
