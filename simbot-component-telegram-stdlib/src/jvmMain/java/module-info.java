module simbot.component.telegram.stdlib {
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires kotlinx.serialization.core;
    requires transitive simbot.component.telegram.api;
    requires org.slf4j;
    requires simbot.logger;
    requires simbot.common.annotations;
    requires simbot.common.suspendrunner;
    requires simbot.common.collection;

    requires static kotlinx.coroutines.reactive;
    requires static org.reactivestreams;

    exports love.forte.simbot.telegram.stdlib.bot;
    exports love.forte.simbot.telegram.stdlib.event;
}
