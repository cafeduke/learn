FROM hypoport/httpd-cgi:latest
WORKDIR /usr/local/apache2
RUN mkdir cert
COPY cert/ cert/
COPY conf/ conf/
COPY htdocs/ htdocs/

