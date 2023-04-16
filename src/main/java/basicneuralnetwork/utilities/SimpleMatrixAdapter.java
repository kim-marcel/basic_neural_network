package basicneuralnetwork.utilities;

import java.lang.reflect.Type;

import org.ejml.simple.SimpleMatrix;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

// This class is needed to make the interfaces/ abstract classes used in this project serializable/ deserializable
// so that they can be converted to JSON or from JSON by Google Gson-library
// The solution was found here:
// https://stackoverflow.com/questions/4795349/how-to-serialize-a-class-with-an-interface/9550086#9550086
public class SimpleMatrixAdapter implements JsonSerializer<SimpleMatrix>, JsonDeserializer<SimpleMatrix> {

	@Override
	public final JsonElement serialize(final SimpleMatrix object, final Type interfaceType,
			final JsonSerializationContext context) {
		final JsonObject member = new JsonObject();
		JsonArray elements = new JsonArray();
		member.addProperty("type", SimpleMatrix.class.getSimpleName());
		member.addProperty("cols", object.numCols());
		member.addProperty("rows", object.numRows());

		for (int i = 0; i < object.getNumElements(); i++) {
			elements.add(object.get(i));
		}
		member.add("values", elements);
		return member;
	}

	@Override
	public final SimpleMatrix deserialize(final JsonElement elem, final Type interfaceType,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject member = (JsonObject) elem;
		final String type = member.get("type").getAsString();
		if (!SimpleMatrix.class.getSimpleName().equals(type)) {
			throw new IllegalArgumentException("Can't deserialize '" + type + "'");
		}
		final int rows = member.get("rows").getAsInt();
		final int cols = member.get("cols").getAsInt();
		SimpleMatrix matrix = new SimpleMatrix(rows, cols);
		JsonArray values = member.get("values").getAsJsonArray();
		int pos = 0;
		for (JsonElement v : values) {
			matrix.set(pos++, v.getAsDouble());
		}
		return matrix;
	}
}
