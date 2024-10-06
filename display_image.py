#!/usr/bin/python
# -*- coding:utf-8 -*-
import sys
import os
import logging

picdir = "../e-Paper/RaspberryPi_JetsonNano/python/pic"
libdir = "../e-Paper/RaspberryPi_JetsonNano/python/lib"

if os.path.exists(libdir):
    sys.path.append(libdir)

from waveshare_epd import epd4in26
import time
from PIL import Image,ImageDraw,ImageFont
import traceback

logging.basicConfig(level=logging.DEBUG)

threshold = 128

try:
    logging.info("epd4in26 Demo")
    epd = epd4in26.EPD()
    
    logging.info("init and Clear")
    epd.init()
    # epd.Clear()

    logging.info("read bmp file")
    epd.init_Fast()
    src_image = Image.open("image.png")
    src_image = src_image.rotate(180)
    gray_image = src_image.convert('L')
    bw_image = gray_image.point(lambda x: 0 if x < threshold else 255, '1')
    img = Image.new('P', bw_image.size, 0)
    palette = [0, 0, 0] * 255 + [255, 255, 255]
    img.putpalette(palette)
    img.paste(bw_image)
    #img = src_image.convert('L')
    # Print the image attributes
    #print("File Format:", img.format)
    #print("File Size:", img.size)
    #print("Color Depth:", image.bits)
    #print("Color Mode:", img.mode)
    epd.display_Fast(epd.getbuffer(img))
    time.sleep(2)

    logging.info("Goto Sleep...")
    epd.sleep()
    
except IOError as e:
    logging.info(e)
    
except KeyboardInterrupt:    
    logging.info("ctrl + c:")
    epd4in26.epdconfig.module_exit(cleanup=True)
    exit()
