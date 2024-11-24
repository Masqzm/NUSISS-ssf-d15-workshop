FROM node:23.1.0

LABEL maintainer="hazim"

## BUILD IMG
WORKDIR /app

# Copy files to image
COPY *.js .
COPY *.json .
COPY public public
COPY views views

# Install pkgs
RUN npm ci

## RUN IMG
ENV PORT=3000

EXPOSE ${PORT}

ENTRYPOINT node main.js