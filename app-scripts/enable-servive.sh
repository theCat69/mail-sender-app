#!/bin/bash

sudo systemctl daemon-reload
sudo systemctl enable mail-sender-app
sudo systemctl start mail-sender-app