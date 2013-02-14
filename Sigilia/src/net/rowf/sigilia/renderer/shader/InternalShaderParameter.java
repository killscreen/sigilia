package net.rowf.sigilia.renderer.shader;

public class InternalShaderParameter implements ShaderParameter<Object> {
	private String decl;
	private String name;
	
	public InternalShaderParameter(String decl, String name) {
		super();
		this.decl = decl;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFullDeclaration() {
		return decl + " " + name + ";";
	}

	@Override
	public boolean usedByFragment() {
		return true;
	}

	@Override
	public boolean usedByVertex() {
		return true;
	}

	@Override
	public void set(Object object, int location) {

	}

	@Override
	public void unset(int location) {
	}

	@Override
	public int getLocationIn(int program) {
		return 0;
	}

}
