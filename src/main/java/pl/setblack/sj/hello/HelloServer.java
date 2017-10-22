package pl.setblack.sj.hello;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class HelloServer {

    public static void main(String[] args) {
        RouterFunction<?> route = route(GET("/"),
                request -> {
                    String headerHTML = "<h1>Witaj na stronie obozu przetrwania</h1>";
                    LocalDateTime  time = LocalDateTime.now();
                    String timeHTML = "Data i godzina:" + time.toString();
                    Mono<String> hi = Mono.just("<body>" +
                                headerHTML +
                                timeHTML +
                            " </body>");
                    return ServerResponse.ok().contentType(TEXT_HTML).body(hi, String.class);
                });


        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter =
                new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create("localhost", 8080);
        server.startAndAwait(adapter);

    }
}
