package net.rowf.sigilia.renderer.shader;

import android.opengl.GLES20;
import net.rowf.sigilia.geometry.Vector;

public enum VectorParameter implements ShaderParameter<Vector> {
	COLOR("uColor"),
	DIRECTION("uDirection") {
		@Override
		public boolean usedByFragment() {
			return false;
		}

		@Override
		public boolean usedByVertex() {
			return true;
		}		
	}
;
	private String   name;

	private VectorParameter(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFullDeclaration() {		
		return "uniform vec3 " + name + ";";
	}

	@Override
	public boolean usedByFragment() {
		return true;
	}

	@Override
	public boolean usedByVertex() {
		return false;
	}

	@Override
	public void set(Vector object, int location) {
		GLES20.glUniform3f(location, object.getX(), object.getY(), object.getZ());
	}

	@Override
	public void unset(int location) {		
	}

	@Override
	public int getLocationIn(int program) {
		return GLES20.glGetUniformLocation(program, name);
	}

}
