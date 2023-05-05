#!/bin/bash

# Verify arguments
set -e

if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ] || [ -z "$4" ]
  then
    echo "Usage: deployArtifact <group_id> <artifact_id> <version> [jar|war]"
    exit 1
fi

echo "Starting deploy artifact!"

# Startup arguments
group_id=${1}
artifact_id=${2}
version=${3}
ext=${4}

# First clean install in base folder to ensure the artifact is present
mvn clean install

# Create new repository directory
base_dir=$(pwd)
target_dir=${base_dir}/target
repo_dir=${base_dir}/repository

# Copy git folder if the directory didn't exist yet
if ! [[ -d ${repo_dir} ]]
then
  mkdir ${repo_dir}
  #echo "Copying git folder from original project..."
  #cp -a ${base_dir}/.git ${repo_dir}/.git
fi

# Check out to new branch called repository
echo "Setting up repository branch and installing jar..."
artifact_location=${target_dir}/${artifact_id}-${version}.${ext}
cd ${repo_dir}
git fetch origin repository
git checkout -B repository
mvn install:install-file -DgroupId=${group_id} -DartifactId=${artifact_id} -Dversion=${version} -Dfile=${artifact_location} -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=. -DcreateChecksum=true

# Commit and push
echo "Committing and pushing to repository branch..."
#git add -A .
#git commit -m "Release version ${version}"
#git push origin repository --force

echo "Done!"
