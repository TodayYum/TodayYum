FROM node:18-alpine as builder

WORKDIR /app

RUN npm install -g pnpm

COPY . /app

RUN pnpm install
RUN pnpm run build

RUN ls -al .

FROM nginx:latest

COPY --from=builder /app/build /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
