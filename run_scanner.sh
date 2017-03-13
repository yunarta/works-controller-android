#!/bin/bash

set -euo pipefail

sonar-scanner -Dsonar.login=$SONAR_TOKEN