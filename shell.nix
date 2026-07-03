{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = with pkgs; [
    libGL
    glib
    xorg.libX11
    xorg.libXext
    xorg.libXrender
    xorg.libXi
    xorg.libXtst
    libxkbcommon
    fontconfig
  ];

  shellHook = ''
    # Corrige os bugs visuais do Wayland/Java
    export GDK_BACKEND=x11
    export _JAVA_AWT_WM_NONREPARENTING=1

    # Injeta as bibliotecas gráficas e o driver de vídeo OpenGL
    export LD_LIBRARY_PATH="/run/opengl-driver/lib:${pkgs.lib.makeLibraryPath [
      pkgs.libGL
      pkgs.glib
      pkgs.xorg.libX11
      pkgs.xorg.libXext
      pkgs.xorg.libXrender
      pkgs.xorg.libXi
      pkgs.xorg.libXtst
      pkgs.libxkbcommon
      pkgs.fontconfig
    ]}:$LD_LIBRARY_PATH:$NIX_LD_LIBRARY_PATH"
  '';
}
