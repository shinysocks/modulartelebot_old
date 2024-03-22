
import asyncio
import colorsys
from kasa import SmartBulb
from telebot.async_telebot import AsyncTeleBot

TOKEN = "6651500714:AAGqGWGXXh1D5GJV-ZSTEps6M17rvvBmemg"
BULB_IP = "192.168.1.69"
BULB_NAME = "bulbington"
TRANSITION = 3000

bot = AsyncTeleBot(TOKEN)

# custom colors
presets = {
    "default": [255, 239, 205],
    "red": [230, 0, 60],
    "orange": [255, 69, 0],
    "yellow": [255, 215, 0],
    "purple": [120, 0, 255],
    "pink": [255, 0, 255],
    "blue": [0, 0, 255],
    "green": [0, 255, 80],
    "white": [255, 255, 255]
}


async def connect():
    global bulb
    try:
        await bulb.update()
    except (Exception):
        print("reconnecting..")
        bulb = SmartBulb(BULB_IP)
        await bulb.set_alias(BULB_NAME)
        await bulb.update()
        print("connected to " + bulb.alias)


@bot.message_handler(commands=['status'])
async def status(message):
    await connect()

    if bulb.is_on:
        photo = f"singlecolorimage.com/get/{await hsv_to_hex(bulb.hsv)}/5x5"
        caption = f'"{bulb.alias}"\n{bulb.brightness}% brightness'
        await bot.send_photo(message.chat.id, photo, caption=caption)
    else:
        await bot.send_message(message.chat.id, f'"{bulb.alias}" is off.')


@bot.message_handler(commands=['rgb'])
async def set(message):
    text = message.text.split(" ")
    text.pop(0)
    hsv = await rgb_to_hsv(text)

    await bulb.set_hsv(hsv[0], hsv[1], hsv[2], transition=TRANSITION)
    await status(message)


@bot.message_handler(commands=['brightness'])
async def brightness(message):
    await bulb.set_brightness(int(message.text.split("/brightness ")[1]), transition=TRANSITION)
    await status(message)


@bot.message_handler(commands=['off'])
async def off(message):
    await bulb.turn_off(transition=TRANSITION)
    await bulb.update()
    await status(message)


@bot.message_handler(commands=['preset'])
async def preset(message):
    await connect()

    preset = message.text.split("/preset ")[1].strip()
    await set_rgb(presets.get(preset))
    await status(message)


async def set_rgb(rgb):
    hsv = await rgb_to_hsv(rgb)
    await bulb.set_hsv(hsv[0], hsv[1], hsv[2], transition=TRANSITION)


async def rgb_to_hex(rgb):
    c = [int(rgb[0]), int(rgb[1]), int(rgb[2])]
    return f"{'%02x%02x%02x' % (c[0], c[1], c[2])}"


async def rgb_to_hsv(rgb):
    c = [int(rgb[0])/255, int(rgb[1])/255, int(rgb[2])/255]
    hsv = colorsys.rgb_to_hsv(c[0], c[1], c[2])
    return [int(hsv[0] * 360), int(hsv[1] * 100), int(hsv[2] * 100)]


async def hsv_to_hex(hsv):
    c = [hsv[0] / 360, hsv[1] / 100, hsv[2] / 100]
    rgb = colorsys.hsv_to_rgb(c[0], c[1], c[2])
    return await rgb_to_hex([int(rgb[0] * 255), int(rgb[1] * 255), int(rgb[2] * 255)])

asyncio.run(connect())
asyncio.run(bot.infinity_polling())
