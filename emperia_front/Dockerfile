# Stage 1: Build the Angular app
FROM node:18.19.1-alpine AS build

WORKDIR /app 

COPY package*.json ./

RUN npm install

RUN npx ngcc --properties es2023 browser module main --first-only --create-ivy-ebtry-points

COPY . .
RUN npm run build


# Stage 2: Serve the app with Nginx
FROM nginx:stable
COPY --from=build /app/dist/emperia_front/browser /usr/share/nginx/html
EXPOSE 80