{ pkgs, lib, config, inputs, ... }:

{
  # https://devenv.sh/basics/
  env.GREET = "devenv";

  # https://devenv.sh/packages/
  packages = [ pkgs.git pkgs.expect ];

  languages.java.enable = true;
  languages.java.jdk.package = pkgs.jdk8;
  languages.java.maven.enable = true;
}

