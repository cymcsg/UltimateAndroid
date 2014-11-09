package com.marshalchen.common.uimodule.tileView.tileview.tiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import com.marshalchen.common.DiskLruCache.DiskLruCache;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class TileCache {
	
	private static final int DISK_CACHE_CAPACITY = 8 * 1024;
	private static final int IO_BUFFER_SIZE = 8 * 1024;
	
	private static final int COMPRESSION_QUALITY = 40;
	
	private static final BitmapFactory.Options BITMAPFACTORY_OPTIONS = new BitmapFactory.Options();
	static {
		BITMAPFACTORY_OPTIONS.inPreferredConfig = Bitmap.Config.RGB_565;
	}

	private MessageDigest digest;
	
	private LruCache<String, Bitmap> memoryCache;
	private DiskLruCache diskCache;

	// TODO: register local broadcast receiver to destroy the cache during onDestroy of containing Activity
	public TileCache( final Context context ) {
		try {
			digest = MessageDigest.getInstance( "MD5" );
		} catch ( NoSuchAlgorithmException e1 ) {
			
		}
		// in memory cache
		final int memory = (int) ( Runtime.getRuntime().maxMemory() / 1024 );
		final int size = memory / 8;
		memoryCache = new LruCache<String, Bitmap>( size ) {
			@Override
			protected int sizeOf( String key, Bitmap bitmap ) {
				// The cache size will be measured in kilobytes rather than number of items.
				// emulate bitmap.getByteCount for APIs less than 12
				int byteCount = bitmap.getRowBytes() * bitmap.getHeight();
				return byteCount / 1024;
			}
		};
		// disk cache
		new Thread(new Runnable(){
			@Override
			public void run(){
				File cacheDirectory = new File( context.getCacheDir().getPath() + File.separator + "com/qozix/mapview" );
				try {
					diskCache = DiskLruCache.open( cacheDirectory, 1, 1, DISK_CACHE_CAPACITY );
				} catch ( IOException e ) {
					
				}
			}
		}).start();
	}

	public void addBitmap( String key, Bitmap bitmap ) {
		addBitmapToMemoryCache( key, bitmap );
		addBitmapToDiskCache( key, bitmap );
	}
	
	public Bitmap getBitmap( String key ) {
		Bitmap bitmap = getBitmapFromMemoryCache( key );
		if ( bitmap == null ) {
			bitmap = getBitmapFromDiskCache( key );
		}
		return bitmap;
	}
	
	public void destroy(){
		memoryCache.evictAll();
		memoryCache = null;
		new Thread(new Runnable(){
			@Override
			public void run(){
				try {
					diskCache.delete();
					diskCache = null;
				} catch ( IOException e ) {
					
				}
			}
		}).start();		
	}
	
	public void clear() {
		memoryCache.evictAll();
		new Thread(new Runnable(){
			@Override
			public void run(){
				try {
					diskCache.delete();
				} catch ( IOException e ) {
					
				}
			}
		}).start();		
	}
	
	private void addBitmapToMemoryCache( String key, Bitmap bitmap ) {
		if ( key == null || bitmap == null ) {
	        return;
	    }
		if ( getBitmapFromMemoryCache( key ) == null ) {
			memoryCache.put( key, bitmap );
		}
	}

	private Bitmap getBitmapFromMemoryCache( String key ) {
		return memoryCache.get( key );
	}
	
	private void addBitmapToDiskCache( String key, Bitmap bitmap ) {
		if ( diskCache == null ) {
			return;
		}
		key = getMD5( key );
		DiskLruCache.Editor editor = null;
		try {
			editor = diskCache.edit( key );
			if ( editor == null ) {
				return;
			}
			OutputStream output = null;
			try {
				output = new BufferedOutputStream( editor.newOutputStream( 0 ), IO_BUFFER_SIZE );
				boolean compressed = bitmap.compress( CompressFormat.JPEG, COMPRESSION_QUALITY, output );
				if ( compressed ) {
					diskCache.flush();
					editor.commit();
				} else {
					editor.abort();
				}
			} finally {
				if ( output != null ) {
					output.close();
				}
			}
		} catch ( IOException e ) {
			try {
				if ( editor != null ) {
					editor.abort();
				}
			} catch ( IOException io ) {
				
			}
		}
	}
	
	private Bitmap getBitmapFromDiskCache( String key ) {
		if ( diskCache == null ) {
			return null;
		}
		key = getMD5( key );
		Bitmap bitmap = null;
		DiskLruCache.Snapshot snapshot = null;
		try {
			snapshot = diskCache.get( key );
			if ( snapshot == null ) {
				return null;
			}
			final InputStream input = snapshot.getInputStream( 0 );
			if ( input != null ) {
				BufferedInputStream buffered = new BufferedInputStream( input, IO_BUFFER_SIZE );
				bitmap = BitmapFactory.decodeStream( buffered, null, BITMAPFACTORY_OPTIONS );
			}
		} catch ( IOException e ) {
			
		} finally {
			if ( snapshot != null ) {
				snapshot.close();
			}
		}
		return bitmap;
	}
	
	private String getMD5( String fileName ) {
		if ( digest != null ) {
			digest.update( fileName.getBytes(), 0, fileName.length() );
			return new BigInteger( 1, digest.digest() ).toString( 16 );
		}
		// if digest is unavailable, at least make some attempt at an acceptable filename
		return fileName.replace("[^a-z0-9_-]", "_");
	}
}
