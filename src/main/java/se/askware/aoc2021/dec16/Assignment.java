package se.askware.aoc2021.dec16;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import se.askware.aoc2021.common.AocBase;

public class Assignment extends AocBase {

	int versionSum = 0;
	String indent = " ";

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	static String hexToBinary(String hex) {
		//System.out.println(hex);
		int i = Integer.parseInt(hex, 16);
		String bin = String.format("%1$4s", Integer.toBinaryString(i)).replace(' ', '0');
		//System.out.println(bin);
		return bin;
	}

	@Override
	public void solvePartOne(List<String> input) {
		for (String string : input) {
			versionSum = 0;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < string.length(); j++) {
				String binary = hexToBinary("" + string.charAt(j));
				sb.append(binary);
			}
			String bstring = sb.toString();
			//System.out.println(bstring);
			List<Packet> parsePacket = parsePacket(new StringReader(bstring), 1);


			System.out.println(versionSum);
		}
	}

	@Override
	public void solvePartTwo(List<String> input) {

		/*
		 * Packets with type ID 0 are sum packets - their value is the sum of the values of their sub-packets. If they only have a single sub-packet, their value is the value of the sub-packet.
		Packets with type ID 1 are product packets - their value is the result of multiplying together the values of their sub-packets. If they only have a single sub-packet, their value is the value of the sub-packet.
		Packets with type ID 2 are minimum packets - their value is the minimum of the values of their sub-packets.
		Packets with type ID 3 are maximum packets - their value is the maximum of the values of their sub-packets.
		Packets with type ID 5 are greater than packets - their value is 1 if the value of the first sub-packet is greater than the value of the second sub-packet; otherwise, their value is 0. These packets always have exactly two sub-packets.
		Packets with type ID 6 are less than packets - their value is 1 if the value of the first sub-packet is less than the value of the second sub-packet; otherwise, their value is 0. These packets always have exactly two sub-packets.
		Packets with type ID 7 are equal to packets - their value is 1 if the value of the first sub-packet is equal to the value of the second sub-packet; otherwise, their value is 0. These packets always have exactly two sub-packets.
		 */

		for (String string : input) {
			versionSum = 0;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < string.length(); j++) {
				String binary = hexToBinary("" + string.charAt(j));
				sb.append(binary);
			}
			String bstring = sb.toString();
			//System.out.println(bstring);
			List<Packet> packets = parsePacket(new StringReader(bstring), 1);

			System.out.println(versionSum);
			System.out.println(packets.get(0).versionSum());
			System.out.println(packets.get(0).getValue());
		}

	}

	private List<Packet> parsePacket(StringReader r) {
		return parsePacket(r, Integer.MAX_VALUE);
	}

	private List<Packet> parsePacket(StringReader r, long numPackets) {
		List<Packet> result = new ArrayList<>();
		
		int localVersionSum = versionSum;
		indent += " ";
		int packageNo = 0;
		int value = 0;
		while (numPackets > 0) {
			String parse = parse(r, 6);
			if (parse.length() < 6 || parse.trim().isEmpty()) {
				indent = indent.substring(1);
				return result;
			}
			Packet p = new Packet();
			result.add(p);

			StringReader r2 = new StringReader(parse);
			int version = parseVersion(r2);
			versionSum += version;
			numPackets--;
			int type = parseType(r2);

			p.version = version;
			p.type = type;

			if (type == 4) {
				p.value = parseLiteralValue(r);
			} else {
				int lengthType = Integer.parseInt(parse(r, 1));
				if (lengthType == 0) {
					long subPacketLength = Long.parseLong(parse(r, 15), 2);
					String subPacket = parse(r, subPacketLength);
					p.subPackages.addAll(parsePacket(new StringReader(subPacket)));
				} else {
					long numPacketsToRead = Long.parseLong(parse(r, 11), 2);
					p.subPackages.addAll(parsePacket(r, numPacketsToRead));
				}

			}
		}
		indent = indent.substring(1);
		return result;
	}

	private static class Packet {
		int version;
		int type;
		long value = -1;
		final List<Packet> subPackages = new ArrayList<>();

		long versionSum() {
			long sum = version;
			for (Packet packet : subPackages) {
				sum += packet.versionSum();
			}
			return sum;
		}

		long getValue() {
			if (type == 0) {
				return subPackages.stream().mapToLong(p -> p.getValue()).sum();
			} else if (type == 1) {
				return subPackages.stream().mapToLong(p -> p.getValue()).reduce(1, (l1, l2) -> l1 * l2);
			} else if (type == 2) {
				return subPackages.stream().mapToLong(p -> p.getValue()).reduce(Long.MAX_VALUE,
						(l1, l2) -> Math.min(l1, l2));
			} else if (type == 3) {
				return subPackages.stream().mapToLong(p -> p.getValue()).reduce(0, (l1, l2) -> Math.max(l1, l2));
			} else if (type == 4) {
				return value;
			} else if (type == 5) {
				return subPackages.get(0).getValue() > subPackages.get(1).getValue() ? 1L : 0L;
			} else if (type == 6) {
				return subPackages.get(0).getValue() < subPackages.get(1).getValue() ? 1L : 0L;
			} else if (type == 7) {
				return subPackages.get(0).getValue() == subPackages.get(1).getValue() ? 1L : 0L;

			}
			return -1;
		}
	}

	int parseVersion(StringReader r) {
		try {
			char[] val = new char[3];
			int read = r.read(val);
			if (read < 3) {
				return -1;
			}
			String s = new String(val);
			if (s.isBlank()) {
				return -1;
			}
			return Integer.parseInt(s, 2);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	int parseType(StringReader r) {
		return Integer.parseInt(parse(r, 3), 2);
	}

	long parseLiteralValue(StringReader r) {

		String valueString = "";
		while (parse(r, 1).equals("1")) {
			valueString += parse(r, 4);
		}
		valueString += parse(r, 4);
		return Long.parseLong(valueString, 2);
	}

	private String parse(StringReader r, long length) {
		try {
			if (length > (long) Integer.MAX_VALUE) {
				throw new RuntimeException("" + length);
			}
			char[] val = new char[(int) length];
			int read = r.read(val);
			String s = new String(val);
			return s;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	private static class BitString {
		String value;

		public BitString(String value) {
			super();
			this.value = value;
		}

		int getVersion() {
			return Integer.parseInt(value.substring(0, 3), 2);
		}

		int getType() {
			return Integer.parseInt(value.substring(3, 6), 2);
		}

		long getLiteralValue() {
			int index = 6;
			String valueString = "";
			while (value.charAt(index) == '1') {
				valueString += value.substring(index + 1, index + 5);
				index += 5;
			}
			valueString += value.substring(index + 1, index + 5);
			return Long.parseLong(valueString, 2);
		}
	}

}
