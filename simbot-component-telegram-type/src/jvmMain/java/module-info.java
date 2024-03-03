module simbot.telegram.type {
    requires kotlin.stdlib;
    requires kotlinx.serialization.core;

    exports love.forte.simbot.telegram.type;
    exports love.forte.simbot.telegram.type.game;
    exports love.forte.simbot.telegram.type.inline;
    exports love.forte.simbot.telegram.type.passport;
    exports love.forte.simbot.telegram.type.payment;
    exports love.forte.simbot.telegram.type.sticker;
    exports love.forte.simbot.telegram.type.update;
}
