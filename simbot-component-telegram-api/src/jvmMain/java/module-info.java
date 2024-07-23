module simbot.component.telegram.api {
    requires kotlin.stdlib;
    requires simbot.common.core;
    requires simbot.common.ktor.inputfile;
    requires transitive simbot.component.telegram.type;
    requires io.ktor.client.core;

    exports love.forte.simbot.telegram.api;
    exports love.forte.simbot.telegram.api.bot;
    exports love.forte.simbot.telegram.api.bot.command;
    exports love.forte.simbot.telegram.api.chat;
    exports love.forte.simbot.telegram.api.message;
    exports love.forte.simbot.telegram.api.update;
    exports love.forte.simbot.telegram.api.user;
    exports love.forte.simbot.telegram.api.utils;
}
