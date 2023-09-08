local keyName = 'ACCOUNT:' .. KEYS[1]
local argValue = ARGV[1]

local dialog = redis.call('GET', keyName)
local decodedDialog = nil
if (dialog and dialog ~= nil) then
  decodedDialog = cjson.decode(dialog);
else
  decodedDialog = cjson.decode('{}')
  decodedDialog.messages = {}
end

table.insert(decodedDialog.messages, 1, argValue)
setmetatable(decodedDialog.messages, cjson.array_mt)

local encode = cjson.encode(decodedDialog)
redis.call('SET', keyName, encode)
return argValue
