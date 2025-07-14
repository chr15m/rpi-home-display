#!/bin/sh

set -e # Exit immediately if a command exits with a non-zero status.

echo "--- START Running setup ---"
date

# Install system dependencies
echo "Installing system dependencies..."
sudo apt-get update
sudo apt-get install -y git npm python3 python3-pip curl wkhtmltoimage

# Clone Waveshare e-Paper library if it doesn't exist
if [ ! -d "../e-Paper" ]; then
  echo "Cloning Waveshare e-Paper library..."
  git clone https://github.com/waveshareteam/e-Paper.git ../e-Paper
else
  echo "Waveshare e-Paper library already exists. Skipping clone."
fi

# Install Python dependencies for the e-Paper library
echo "Installing Python dependencies for e-Paper library..."
sudo pip3 install RPi.GPIO spidev Pillow

# Install Node.js dependencies
echo "Installing Node.js dependencies..."
npm install

echo "--- DONE Setup complete ---"
