#!/bin/bash

set -euo pipefail

sonar-scanner -Dsonar.login=$SONAR_TOKEN -Dsonar.projectVersion=$TRAVIS_BUILD_NUMBER