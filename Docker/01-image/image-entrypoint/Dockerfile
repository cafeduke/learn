# Use alpine as the base
FROM alpine:latest

# Run update and install bash
RUN /bin/sh -c 'apk update; \
apk add bash'

# Change pwd to /home
WORKDIR /home

# Copy arg.sh to WORKDIR
COPY arg.sh .
ENV MY_FRUIT=Mango

ENTRYPOINT ["/home/arg.sh"]
CMD ["HelloWorld!"]
