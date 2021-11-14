# TP3 Bibliothèque Apple Team

[![Build Status](https://travis-ci.com/dptinfoutln/tp3-bibliotheque-appleteam.svg?token=uPLp8DjFf4Lmc7vssqXH&branch=develop)](https://travis-ci.com/dptinfoutln/tp3-bibliotheque-appleteam) develop

[![Build Status](https://travis-ci.com/dptinfoutln/tp3-bibliotheque-appleteam.svg?token=uPLp8DjFf4Lmc7vssqXH&branch=master)](https://travis-ci.com/dptinfoutln/tp3-bibliotheque-appleteam) master

Cette exemple utilise un compilation multi stage avec buildkit.

Compiler avec
`COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose build`

Pousser sur dockerhub avec
`COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose push`

Exécuter avec
`COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up`
Ajouter -d pour lancer en daemon
et/ou --build

Ensuite pour accéder à l'API cliquez sur le lien suivaint : 
http://localhost:8081/library/application.wadl
