package main;

import lombok.Getter;

public enum OffsetData {
	JSLTZ_OFFSET(new int[][][] {
		{{0, 0}, {0, 0}, {0, 0}, {0, 0}},
		{{0, 0}, {1, 0}, {0, 0}, {-1, 0}},
		{{0, 0}, {1, -1}, {0, 0}, {-1, -1}},
		{{0, 0}, {0, 2}, {0, 0}, {0, 2}},
		{{0, 0}, {1, 2}, {0, 0}, {-1, 2}}
	}),
	I_OFFSET(new int[][][] {
		{{0, 0}, {-1, 0}, {-1, 1}, {0, 1}},
		{{-1, 0}, {0, 0}, {1, 1}, {0, 1}},
		{{2, 0}, {0, 0}, {-2, 1}, {0, 1}},
		{{-1, 0}, {0, 1}, {1, 0}, {0, -1}},
		{{2, 0}, {0, -2}, {-2, 0}, {0, 2}}
	});
	
	@Getter private int[][][] offsetData;
	
	private OffsetData(int[][][] offsetData) {
		this.offsetData = offsetData;
	}
	
}
