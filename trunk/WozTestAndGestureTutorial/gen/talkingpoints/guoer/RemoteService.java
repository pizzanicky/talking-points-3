/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/sangmipa/Documents/workspace/BluetoothScanner/src/talkingpoints/guoer/RemoteService.aidl
 */
package talkingpoints.guoer;
//import com.android.widget.ArrayAdapter;

public interface RemoteService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements talkingpoints.guoer.RemoteService
{
private static final java.lang.String DESCRIPTOR = "talkingpoints.guoer.RemoteService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an talkingpoints.guoer.RemoteService interface,
 * generating a proxy if needed.
 */
public static talkingpoints.guoer.RemoteService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof talkingpoints.guoer.RemoteService))) {
return ((talkingpoints.guoer.RemoteService)iin);
}
return new talkingpoints.guoer.RemoteService.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getCounter:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCounter();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getBTList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.getBTList();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_getLac:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getLac();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getLng:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getLng();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getWIFI:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.getWIFI();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements talkingpoints.guoer.RemoteService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public int getCounter() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCounter, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public java.util.List<java.lang.String> getBTList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBTList, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public java.lang.String getLac() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLac, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public java.lang.String getLng() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLng, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public java.util.List<java.lang.String> getWIFI() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getWIFI, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getCounter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getBTList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getLac = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getLng = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getWIFI = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public int getCounter() throws android.os.RemoteException;
public java.util.List<java.lang.String> getBTList() throws android.os.RemoteException;
public java.lang.String getLac() throws android.os.RemoteException;
public java.lang.String getLng() throws android.os.RemoteException;
public java.util.List<java.lang.String> getWIFI() throws android.os.RemoteException;
}
