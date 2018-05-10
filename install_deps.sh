#!/usr/bin/env bash

DOTTY_HOME=$1
DOTTY_RECORDS_JAR=$2

if [ -z "$DOTTY_HOME" ]; then
  echo "$0: no DOTTY_HOME given" >&2
  echo "" >&2
  echo "usage: $0 DOTTY_HOME DOTTY_RECORDS_JAR" >&2
  exit 1
fi

if [ -z "$DOTTY_RECORDS_JAR" ]; then
  echo "$0: no DOTTY_RECORDS_JAR path given" >&2
  echo "" >&2
  echo "usage: $0 DOTTY_HOME DOTTY_RECORDS_JAR" >&2
  exit 1
fi


(cd $DOTTY_HOME && sbt "project dist-bootstrapped" pack)

DOTTY_LIB_SRC="$DOTTY_HOME/dist-bootstrapped/target/pack/lib"
DOTTY_LIB_TRG=repo/ch/epfl/lamp

mkdir -p "$DOTTY_LIB_TRG/dotty-library_0.8/0.8.0-RC1-bin-SNAPSHOT"  \
&& cp "$DOTTY_LIB_SRC/dotty-library_0.8-0.8.0-RC1-bin-SNAPSHOT.jar" "$DOTTY_LIB_TRG/dotty-library_0.8/0.8.0-RC1-bin-SNAPSHOT/dotty-library_0.8-0.8.0-RC1-bin-SNAPSHOT.jar"

mkdir -p "$DOTTY_LIB_TRG/dotty-compiler_0.8/0.8.0-RC1-bin-SNAPSHOT" \
&& cp "$DOTTY_LIB_SRC/dotty-compiler_0.8-0.8.0-RC1-bin-SNAPSHOT.jar" "$DOTTY_LIB_TRG/dotty-compiler_0.8/0.8.0-RC1-bin-SNAPSHOT/dotty-compiler_0.8-0.8.0-RC1-bin-SNAPSHOT.jar"

mkdir -p "$DOTTY_LIB_TRG/dotty-interfaces/0.8.0-RC1-bin-SNAPSHOT" \
&& cp "$DOTTY_LIB_SRC/dotty-interfaces-0.8.0-RC1-bin-SNAPSHOT.jar" "$DOTTY_LIB_TRG/dotty-interfaces/0.8.0-RC1-bin-SNAPSHOT/dotty-interfaces-0.8.0-RC1-bin-SNAPSHOT.jar"

mkdir -p repo/se/obkson/dotty-records_0.8/0.1 \
&& cp $DOTTY_RECORDS_JAR repo/se/obkson/dotty-records_0.8/0.1/dotty-records_0.8-0.1.jar


# Uncomment to automatically purge the local maven cache:
#rm -r ~/.m2/repository/ch/epfl/lamp
#rm -r ~/.m2/repository/se/obkson
