package com.fss.common.uiModule.tileView.tileview.detail;

public class DetailLevelPatternParserDefault implements DetailLevelPatternParser {

	@Override
	public String parse( String pattern, int row, int column ) {
		return pattern.replace( "%col%", Integer.toString( column ) ).replace( "%row%", Integer.toString( row ) );
	}

}
