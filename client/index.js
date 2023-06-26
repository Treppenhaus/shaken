const mineflayer = require('mineflayer')
const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
});


const bot = mineflayer.createBot({
    host: 'localhost', // minecraft server ip
    username: 'treppi', // minecraft username
    auth: 'offline', // for offline mode servers, you can set this to 'offline'
    // port: 25565,                // only set if you need a port that isn't 25565
     version: "1.19",             // only set if you need a specific version or snapshot (ie: "1.8.9" or "1.16.5"), otherwise it's set automatically
    // password: '12345678'        // set if you want to use password-based auth (may be unreliable)
})

bot.on('chat', (username, message) => {
    if (username === bot.username) return
    console.log(message)
})

// Log errors and kick reasons:
bot.on('kicked', console.log)
bot.on('error', console.log)

async function chat() {
    await readline.question('msg', msg => {
        console.log("sending " + msg)
        bot.chat(msg);
        readline.close();
    });
}

async function b() {
    await chat();
    await chat();
}
b();
