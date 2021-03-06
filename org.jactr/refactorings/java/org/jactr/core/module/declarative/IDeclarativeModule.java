/*
 * Created on Aug 14, 2006 Copyright (C) 2001-6, Anthony Harrison anh23@pitt.edu
 * (jactr.org) This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version. This library is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jactr.core.module.declarative;

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.jactr.core.chunk.IChunk;
import org.jactr.core.chunktype.IChunkType;
import org.jactr.core.module.IModule;
import org.jactr.core.module.declarative.event.IDeclarativeModuleListener;
import org.jactr.core.production.request.ChunkTypeRequest;

/**
 * one of two specialized modules, this one handles all declarative memory
 * operations for the model, in particular adding and retrieving of chunks,
 * chunktypes all operation return values are wrapped in Future<> to better
 * support backed concurrencies
 * 
 * @author developer
 * @see java.util.concurrent.Future
 */
public interface IDeclarativeModule extends IModule
{

  /**
   * create a new chunktype to be added after its symbolic contents have been
   * set. typically this will just delegate to the factory methods, but is
   * provided here so that declarative modules can insert custom creators
   * 
   * @param parent
   *            maybe null
   * @param name
   * @return
   */
  public Future<IChunkType> createChunkType(IChunkType parent, String name);

  /**
   * add the chunktype to the model. this chunktype should have been created by
   * createChunkType(). It will call the IChunkType.encode() method, add this
   * chunktype to the parent's list of children (if there is a parent) and then
   * add it to the internal data stores
   * 
   * @param chunkType
   * @return
   */
  public Future<IChunkType> addChunkType(IChunkType chunkType);

  /**
   * return the named chunktype. Case insensitive, but preserving
   * 
   * @param name
   * @return
   */
  public Future<IChunkType> getChunkType(String name);

  /**
   * return all the chunk types in this model
   * 
   * @return
   */
  public Future<Collection<IChunkType>> getChunkTypes();

  /**
   * create a chunk to later be inserted.
   * 
   * @param parent
   *            must not be null (duh)
   * @param name
   * @return
   */
  public Future<IChunk> createChunk(IChunkType parent, String name);
  
  
  /**
   * request that this chunk be disposed.
   * @param chunk
   */
  public void dispose(IChunk chunk);
  


  /**
   * return a copy of source chunk
   * 
   * @param sourceChunk
   * @return
   */
  public Future<IChunk> copyChunk(IChunk sourceChunk);

  /**
   * add this chunk to the model and optionally check for duplicates so that it
   * can be merged if necessary
   * 
   * @param chunk
   * @param checkForDuplicates
   * @return a future wrapper of the actual chunk reference that was installed.
   *         if the chunk was actually merged, the original chunk is returned
   */
  public Future<IChunk> addChunk(IChunk chunk);

  /**
   * because encoding might be async, we need a method to determine if a given
   * chunk is scheduled for encoding
   * 
   * @param chunk
   * @return
   */
  public boolean willEncode(IChunk chunk);

  /**
   * return the named chunk, case insensitive but preserving
   * 
   * @param name
   * @return
   */
  public Future<IChunk> getChunk(String name);

  /**
   * return all chunks. This can be a <b>very</b> expensive operation
   * 
   * @return
   */
  public Future<Collection<IChunk>> getChunks();

  /**
   * return the number of chunks in the model. this might be an estimate
   * 
   * @return
   */
  public long getNumberOfChunks();

  /**
   * search DM for all the chunks that match pattern, sorting using sorter, that
   * are above activationThreshold
   * 
   * @param request
   * @param sorter
   *            may be null
   * @param activationThreshold
   * @param bestOne
   *            find only best one
   * @return
   */
  public Future<Collection<IChunk>> findExactMatches(ChunkTypeRequest request,
      Comparator<IChunk> sorter, double activationThreshold, boolean bestOne);

  /**
   * search DM for all the chunks that partially match
   * 
   * @param request
   * @param sorter
   *            may be null
   * @param activationThreshold
   * @param bestOne
   *            true if you only want the best one chunk
   * @return
   */
  public Future<Collection<IChunk>> findPartialMatches(ChunkTypeRequest request,
      Comparator<IChunk> sorter, double activationThreshold, boolean bestOne);

  /**
   * snag the busy chunk.<br>
   * <br>
   * <b>Note</b> : this should not be called by the declarative memory module
   * if the retrieval will access the future methods as it might result in
   * deadlock.
   * 
   * @return
   */
  public IChunk getBusyChunk();

  /**
   * snag the busy chunk.<br>
   * <br>
   * <b>Note</b> : this should not be called by the declarative memory module
   * if the retrieval will access the future methods as it might result in
   * deadlock.
   * 
   * @return
   */
  public IChunk getEmptyChunk();

  /**
   * snag the busy chunk.<br>
   * <br>
   * <b>Note</b> : this should not be called by the declarative memory module
   * if the retrieval will access the future methods as it might result in
   * deadlock.
   * 
   * @return
   */
  public IChunk getErrorChunk();

  /**
   * snag the busy chunk.<br>
   * <br>
   * <b>Note</b> : this should not be called by the declarative memory module
   * if the retrieval will access the future methods as it might result in
   * deadlock.
   * 
   * @return
   */
  public IChunk getFreeChunk();

  /**
   * snag the busy chunk.<br>
   * <br>
   * <b>Note</b> : this should not be called by the declarative memory module
   * if the retrieval will access the future methods as it might result in
   * deadlock.
   * 
   * @return
   */
  public IChunk getFullChunk();

  /**
   * snag the busy chunk.<br>
   * <br>
   * <b>Note</b> : this should not be called by the declarative memory module
   * if the retrieval will access the future methods as it might result in
   * deadlock.
   * 
   * @return
   */
  public IChunk getNewChunk();

  /**
   * snag the busy chunk.<br>
   * <br>
   * <b>Note</b> : this should not be called by the declarative memory module
   * if the retrieval will access the future methods as it might result in
   * deadlock.
   * 
   * @return
   */
  public IChunk getRequestedChunk();

  /**
   * snag the busy chunk.<br>
   * <br>
   * <b>Note</b> : this should not be called by the declarative memory module
   * if the retrieval will access the future methods as it might result in
   * deadlock.
   * 
   * @return
   */
  public IChunk getUnrequestedChunk();

  public void addListener(IDeclarativeModuleListener listener, Executor executor);

  public void removeListener(IDeclarativeModuleListener listener);

// public void addListener(ISearchListener listener, Executor executor);
//
// public void removeListener(ISearchListener listener);
}
