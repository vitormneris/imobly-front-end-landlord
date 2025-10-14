{
	description = "Ambiente para desenvolvimento com KMP+CMP";

	inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

	outputs = { self, nixpkgs }: {
		devShells.x86_64-linux.default =
		let
			pkgs = import nixpkgs {
				system = "x86_64-linux";
				config = { allowUnfree = true; };
			};
		in
			pkgs.mkShell {
				packages = with pkgs; [
					jdk
					nodejs
					yarn
					gradle_9
				];

				shellHook = ''
					echo "Ambiente dev iniciado!"
					java --version
				'';
			};
   };
}